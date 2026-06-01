package BLL;


public class Pedido {
    private Cliente cliente_id;
    private String tipoDeporte;
    private Cliente cliente;
 

    public Pedido(Cliente cliente_id, String tipoDeporte) {
        this.cliente_id = cliente_id;
        this.tipoDeporte = tipoDeporte;
    }

  
    public Cliente getCliente() {
        return cliente_id;
    }

    public String getTipoDeporte() {
        return tipoDeporte;
    }

    @Override
    public String toString() {
        return "Pedido de " + cliente_id + " - Deporte: " + tipoDeporte;
    }
}