package logic;

import java.util.ArrayList;
import java.util.List;

public class PokerRangeCalculator {
    public static void main(String[] args) {
        List<String> range = new ArrayList<>();
        range.add("AA");
        range.add("JJ");
        range.add("ATs-A8s");
        range.add("76o");
        range.add("54o");
        
        double porcentaje = calcularPorcentajeRango(range);
        
        System.out.println("El porcentaje de manos en el rango es: " + porcentaje);
    }
    
    public static double calcularPorcentajeRango(List<String> rango) {
        int totalCombinaciones = 0;
        int combinacionesSuperiores = 0;
        
        List<String> todasLasManos = generarTodasLasManos();
        
        for (String mano : todasLasManos) {
            totalCombinaciones++;
            
            if (manoEnRango(mano, rango)) {
                combinacionesSuperiores++;
            }
        }
        
        double porcentaje = (combinacionesSuperiores / (double) totalCombinaciones) * 100;
        
        return porcentaje;
    }
    
    public static List<String> generarTodasLasManos() {
        // Lógica para generar todas las posibles combinaciones de manos
        // Esto puede incluir la generación de parejas, combinaciones de cartas suited y offsuit, etc.
        // Utiliza bucles y listas para generar todas las combinaciones
        
        List<String> todasLasManos = new ArrayList<>();
        
        // Agregar todas las manos posibles a la lista
        
        return todasLasManos;
    }
    
    public static boolean manoEnRango(String mano, List<String> rango) {
        // Lógica para determinar si una mano está en el rango dado
        // Puedes utilizar estructuras de datos como listas o conjuntos para representar el rango
        
        return rango.contains(mano);
    }
}
