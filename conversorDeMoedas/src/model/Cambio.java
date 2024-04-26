package model;

public record Cambio(String base_code, String target_code, Double conversion_result) {

    public String toString(){
        return " base: "+ this.base_code + "\n para: " + this.target_code + "\n Valor convertido: " + conversion_result;
    }
}
