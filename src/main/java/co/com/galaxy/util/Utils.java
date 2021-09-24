package co.com.galaxy.util;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class Utils {
    public final Logger LOG = LoggerFactory.getLogger(Utils.class);

    public static final String VACIO = "";
    public static final String SEQUIA = "sequia";
    public static final String OPTIMO = "optimo";
    public static final String LLUVIA = "lluvia";
    public static final String DESCONOCIDO = "desconocido";
    public static final String TORMENTA = "tormenta";

    public static final Integer TRES = 3;

    public static final String ULTIMO_DIA = "ultimoDia";
    public static final String FECHA_ULTIMO_DIA = "fechaUltimoDia";

    public static final String NO_ENCONTRADO = "Clima no encontrado";
}
