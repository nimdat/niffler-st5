package guru.qa.niffler.data.repository;

import guru.qa.niffler.data.entity.CategoryEntity;
import guru.qa.niffler.data.entity.SpendEntity;

import java.util.List;

public class SpendRepositorySpringJdbc implements SpendRepository {
    @Override
    public CategoryEntity createCategory(CategoryEntity category) {
        return null;
    }

    @Override
    public CategoryEntity editCategory(CategoryEntity category) {
        return null;
    }

    @Override
    public void removeCategory(CategoryEntity category) {

    }

    @Override
    public SpendEntity createSpand(SpendEntity spend) {
        return null;
    }

    @Override
    public SpendEntity editSpand(SpendEntity spend) {
        return null;
    }

    @Override
    public void removeSpend(SpendEntity spend) {

    }

    @Override
    public List<SpendEntity> findAllByUsername(String username) {
        return List.of();
    }
}