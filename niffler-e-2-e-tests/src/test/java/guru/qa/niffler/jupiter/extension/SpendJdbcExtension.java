package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;
import guru.qa.niffler.data.repository.SpendRepository;
import guru.qa.niffler.jupiter.annotation.GenerateSpend;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.model.SpendJson;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Date;

public class SpendJdbcExtension extends AbstractSpendExtension {
    private final SpendRepository spendRepository = SpendRepository.getInstance();

    @Override
    protected SpendJson createSpend(ExtensionContext context, GenerateSpend spend) {
        CategoryJson category = context.getStore(CategoryHttpExtension.NAMESPACE).get(context.getUniqueId(), CategoryJson.class);

        SpendEntity spendEntity = new SpendEntity();
        spendEntity.setSpendDate(new Date());
        spendEntity.setCategory(CategoryEntity.fromJson((category)));
        spendEntity.setCurrency(spend.currency());
        spendEntity.setAmount(spend.amount());
        spendEntity.setDescription(spend.description());
        spendEntity.setUsername(category.username());

        return SpendJson.fromEntity(spendRepository.createSpand(spendEntity));
    }

    @Override
    protected void removeSpend(SpendJson spend) {

    }
}