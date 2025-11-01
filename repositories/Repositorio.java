package repositories;

import java.util.List;

public interface Repositorio<T> {
    void salvar(T entidade);
    T buscarPorId(int id);
    List<T> listarTodos();
    boolean existe(int id);
}
