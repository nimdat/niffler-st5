package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.jupiter.annotation.GenerateCategory;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoryExtension implements BeforeEachCallback, AfterEachCallback {

    public static final ExtensionContext.Namespace NAMESPACE
            = ExtensionContext.Namespace.create(CategoryExtension.class);

//    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
//            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BODY))
//            .build();
//
//    private final Retrofit retrofit = new Retrofit.Builder()
//            .client(okHttpClient)
//            .baseUrl("http://127.0.0.1:8093/")
//            .addConverterFactory(JacksonConverterFactory.create())
//            .build();

    private final SpendRepository spendRepository = SpendRepository.getInstance();

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
//        CategoryApi categoryApi = retrofit.create(CategoryApi.class);



        AnnotationSupport.findAnnotation(
                extensionContext.getRequiredTestMethod(),
                GenerateCategory.class
        ).ifPresent(
                cat -> {
                    CategoryJson categoryJson = new CategoryJson(
                            null,
                            cat.category(),
                            cat.username()
                    );
//                    try {
                        CategoryEntity category = new CategoryEntity();
                        category.setCategory(cat.category());
                        category.setUsername(cat.username());

                        category = spendRepository.createCategory(category);

//                        CategoryJson result = Objects.requireNonNull(
//                                categoryApi.createCategory(categoryJson).execute().body()
//                        );
                        extensionContext.getStore(NAMESPACE).put(
                                extensionContext.getUniqueId(), CategoryJson.fromEntity(category)
                        );
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
                }
        );
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        CategoryJson categoryJson = context.getStore(NAMESPACE).get(context.getUniqueId(), CategoryJson.class);
        spendRepository.removeCategory(CategoryEntity.fromJson(categoryJson));
    }
}