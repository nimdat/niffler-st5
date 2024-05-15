package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;

public interface SpendRepository {

    static SpendRepository getInstance() {
        return new SpendRepositoryJdbc();
    }

    CategoryEntity createCategory(CategoryEntity category);

    void removeCategory(CategoryEntity category);
}