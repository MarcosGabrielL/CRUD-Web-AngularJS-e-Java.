package produtos;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("produtos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoService {

    @PersistenceContext(unitName = "ProdutosPU")
    private EntityManager entityManager;

    public ProdutoService() {
    }

    @GET
    public List<Produto> getProdutos() {
        Query query = entityManager.createQuery("SELECT p FROM Produto p");
        return query.getResultList();
    }

    @POST
    public Produto adicionar(Produto produto) {
        entityManager.persist(produto);
        return produto;
    }

    @PUT
    @Path("{id}")
    public Produto atualizar(@PathParam("id") Integer id, Produto produtoAtualizado) {
        Produto produtoEncontrado = getProduto(id);
        produtoEncontrado.setDescricao(produtoAtualizado.getDescricao());
        produtoEncontrado.setPreco(produtoAtualizado.getPreco());
        entityManager.merge(produtoEncontrado);
        return produtoEncontrado;
    }

    @DELETE
    @Path("{id}")
    public Produto excluir(@PathParam("id") Integer id) {
        Produto produto = getProduto(id);
        entityManager.remove(produto);
        return produto;
    }
    
    @GET
    @Path("{id}")
    public Produto getProduto(@PathParam("id") Integer id) {
        return entityManager.find(Produto.class, id);
    }
    
    @POST
    @Path("{id}/movimentacoes")
    public Produto adicionarMovimentacao(@PathParam("id") Integer id, Movimentacao m) {
        Produto produto = getProduto(id);
        m.setProduto(produto);
        entityManager.persist(m);
        produto.getMovimentacoes().add(m);
        return produto;
    }

    @DELETE
    @Path("{id}/movimentacoes/{idMovimentacao}")
    public Produto excluirMovimentacao(@PathParam("id") Integer id,
            @PathParam("idMovimentacao") Integer idMovimentacao) {
        Produto produto = getProduto(id);
        Movimentacao movimentacao = getMovimentacao(idMovimentacao);
        entityManager.remove(movimentacao);
        produto.getMovimentacoes().remove(movimentacao);
        return produto;
    }

    private Movimentacao getMovimentacao(Integer idMovimentacao) {
        return entityManager.find(Movimentacao.class, idMovimentacao);
    }
    
    @PUT
    @Path("{id}/movimentacoes/{idMovimentacao}")
    public Produto atualizarMovimentacao(@PathParam("id") Integer id,
            @PathParam("idMovimentacao") Integer idMovimentacao,
            Movimentacao movimentacaoAtualizada) {
        
        movimentacaoAtualizada.setProduto(getProduto(id));
        entityManager.merge(movimentacaoAtualizada);
        return getProduto(id);
    }
    
}
