package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.SpendApi;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.junit.jupiter.api.extension.ExtensionContext;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

public class SpendHttpExtension extends AbstractSpendExtension {

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BODY))
            .build();

    private final Retrofit retrofit = new Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://127.0.0.1:8093/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    @Override
    protected SpendJson createSpend(ExtensionContext context, GenerateSpend spend) {
        SpendApi spendApi = retrofit.create(SpendApi.class);
        CategoryJson category = context.getStore(CategoryHttpExtension.NAMESPACE).get(context.getUniqueId(), CategoryJson.class);

        SpendJson spendJson = new SpendJson(
                null,
                new Date(),
                category.category(),
                spend.currency(),
                spend.amount(),
                spend.description(),
                category.username()
        );

        try {
            return Objects.requireNonNull(
                    spendApi.createSpend(spendJson).execute().body());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void removeSpend(SpendJson spend) {

    }
}