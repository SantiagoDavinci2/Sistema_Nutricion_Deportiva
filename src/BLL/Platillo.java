package BLL;


public class Platillo {
    private String nombre;
    private String tipoDeporte;
    private String restricciones;
    private String ingredientes;
    private String porcion;
    private String tipoComida;

    public Platillo(String nombre, String tipoDeporte, String restricciones,
                    String ingredientes, String porcion, String tipoComida) {
        this.nombre = nombre;
        this.tipoDeporte = tipoDeporte;
        this.restricciones = restricciones;
        this.ingredientes = ingredientes;
        this.porcion = porcion;
        this.tipoComida = tipoComida;
    }

    public String toString() {
        return "" + nombre +
                " | Deporte: " + tipoDeporte +
                " | Restricción: " + restricciones +
                "\nIngredientes: " + ingredientes +
                "\nPorción/Macros: " + porcion +
                "\nTipo de comida: " + tipoComida;
    }


     public String getNombre() {
    return nombre;
              }

     public String getTipoDeporte() {
    return tipoDeporte;
         }

   public String getRestricciones() {
    return restricciones;
     }

   public String getIngredientes() {
    return ingredientes;
   }

   public String getPorcion() {
    return porcion;
   }

   public String getTipoComida() {
    return tipoComida;
   }

}



