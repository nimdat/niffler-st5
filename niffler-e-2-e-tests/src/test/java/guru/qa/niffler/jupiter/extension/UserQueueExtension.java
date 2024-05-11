package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.jupiter.annotation.User;
import guru.qa.niffler.model.UserJson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static guru.qa.niffler.jupiter.annotation.User.UserType.*;
import static guru.qa.niffler.model.UserJson.simpleUser;

public class UserQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(UserQueueExtension.class);

    private static final Map<User.UserType, Queue<UserJson>> USERS = new ConcurrentHashMap<>();

    static {
        Queue<UserJson> friendsQueue = new ConcurrentLinkedQueue<>();
        Queue<UserJson> invitationSentQueue = new ConcurrentLinkedQueue<>();
        Queue<UserJson> invitationReceivedQueue = new ConcurrentLinkedQueue<>();

        friendsQueue.add(simpleUser("user1", "12345"));
        friendsQueue.add(simpleUser("user2", "12345"));
        invitationSentQueue.add(simpleUser("user3", "12345"));
        invitationSentQueue.add(simpleUser("user4", "12345"));
        invitationReceivedQueue.add(simpleUser("user5", "12345"));
        invitationReceivedQueue.add(simpleUser("user6", "12345"));

        USERS.put(WITH_FRIENDS, friendsQueue);
        USERS.put(INVITATION_SEND, invitationSentQueue);
        USERS.put(INVITATION_RECEIVED, invitationReceivedQueue);
    }

    private static List<Parameter> getParameters(ExtensionContext context) {
        List<Method> allMethods = new ArrayList<>();

        allMethods.add(context.getRequiredTestMethod());
        Arrays.stream(context.getRequiredTestClass().getDeclaredMethods())
                .filter(f -> f.isAnnotationPresent(BeforeEach.class)).forEach(allMethods::add);

        List<Parameter> parameters = allMethods.stream()
                .map(Executable::getParameters)
                .flatMap(Arrays::stream)
                .filter(parameter -> parameter.isAnnotationPresent(User.class))
                .filter(parameter -> parameter.getType().isAssignableFrom(UserJson.class)).toList();

        return parameters;
    }


    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        List<Parameter> parameters = getParameters(context);
        Map<User.UserType, UserJson> testCandidates = new HashMap<>();

        for (Parameter parameter : parameters) {
            User.UserType userType = parameter.getAnnotation(User.class).value();
            if (testCandidates.containsKey(userType)) {
                continue;
            }

            UserJson testCandidate = null;
            Queue<UserJson> queue = USERS.get(userType);
            while (testCandidate == null) {
                testCandidate = queue.poll();
            }
            testCandidates.put(userType, testCandidate);
        }
        context.getStore(NAMESPACE).put(context.getUniqueId(), testCandidates);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Map<User.UserType, UserJson> userFromTest = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);

        for (User.UserType userType : userFromTest.keySet()) {
            USERS.get(userType).add(userFromTest.get(userType));
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(UserJson.class) &&
                parameterContext.getParameter().isAnnotationPresent(User.class);
    }

    @Override
    public UserJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return (UserJson) extensionContext.getStore(NAMESPACE)
                .get(extensionContext.getUniqueId(), Map.class)
                .get(parameterContext.findAnnotation(User.class).get().value());
    }
}