package BLL;
public class Cliente {
	private int id;
    private String nombre;
    private int cantidadPersonas;
    private String restricciones;
    private String tipoDeporte;
    private int user_id;

    public Cliente(int id, String nombre, int cantidadPersonas, String restricciones, String tipoDeporte,int user_id) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadPersonas = cantidadPersonas;
        this.restricciones = restricciones;
        this.tipoDeporte = tipoDeporte;
        this.user_id = user_id;
    }
    
    public Cliente(int id, String nombre, int cantidadPersonas, String restricciones, String tipoDeporte) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadPersonas = cantidadPersonas;
        this.restricciones = restricciones;
        this.tipoDeporte = tipoDeporte;
    }
    
    public Cliente(String nombre, int cantidadPersonas, String restricciones) {
        this.nombre = nombre;
        this.cantidadPersonas = cantidadPersonas;
        this.restricciones = restricciones;
    }
    
   
    
    
    
    public Cliente(String nombre, int cantidadPersonas, String restricciones, String tipoDeporte) {
        this.nombre = nombre;
        this.cantidadPersonas = cantidadPersonas;
        this.restricciones = restricciones;
        this.tipoDeporte = tipoDeporte;
    }
    

    
    public Cliente(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public String getRestricciones() {
        return restricciones;
    }

    public int getUser_id() {
    	return user_id;
    }
   
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTipoDeporte() {
    	return tipoDeporte;
    }
    
    public void setTipoDeporte(String tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }
    
    public void setRestricciones(String restricciones) {
    	  this.restricciones = restricciones;
    }
    
    public void setcantidadPersonas(int cantidadPersonas) {
    	this.cantidadPersonas = cantidadPersonas;
    }
    

    @Override
    public String toString() {
        return nombre + " (" + cantidadPersonas + " personas, restricciones: " + restricciones + ")";
    }
}