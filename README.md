### <center>**Problema a solucionar**</center>
En una galaxia lejana, existen tres civilizaciones. Vulcanos, Ferengis y Betasoides. Cada
civilización vive en paz en su respectivo planeta.
Dominan la predicción del clima mediante un complejo sistema informático.

**Premisas:**
- El planeta Ferengi se desplaza con una velocidad angular de 1 grados/día en sentido horario. Su distancia con respecto al sol es de 500Km.
- El planeta Betasoide se desplaza con una velocidad angular de 3 grados/día en sentido horario. Su distancia con respecto al sol es de 2000Km.
- El planeta Vulcano se desplaza con una velocidad angular de 5 grados/día en sentido anti­horario, su distancia con respecto al sol es de 1000Km.
- Todas las órbitas son circulares.

Cuando los tres planetas están alineados entre sí y a su vez alineados con respecto al sol, el
sistema solar experimenta un período de **sequía**.

Cuando los tres planetas no están alineados, forman entre sí un triángulo. Es sabido que en el
momento en el que el sol se encuentra dentro del triángulo, el sistema solar experimenta un
período de **lluvia**, teniendo éste, un **pico de intensidad** cuando el perímetro del triángulo está en
su máximo.

Las condiciones **óptimas de presión y temperatura** se dan cuando los tres planetas están
alineados entre sí pero no están alineados con el sol.

Realizar un programa informático para poder predecir en los próximos 10 años:
1. ¿Cuántos períodos de sequía habrá?
2. ¿Cuántos períodos de lluvia habrá y qué día será el pico máximo de lluvia?
3. ¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?

### <center>**Solución**</center>
#### **Consideraciones:**

- Cada año durará 360 días ya que es el tiempo que le toma dar una vuelta al planeta mas lento (Ferengi)
- Para calcular en que punto esta cada planeta diariamente (dentro de un plano cartesiano), se usa la formula de fisica de Movimiento Circular Uniforme (MCU):
```
x = r cos(w t s)
x = r sen(w t s)

r = Distancia respecto al sol
w = Velocidad angular
t = Tiempo de desplazamiento
s = sentido (-1=Horario, 1=Antihorario)
```
- Para calcular el area de un triangulo hay dos formas: Matriz y producto. Usaré la formula de producto:
```
Sean A, B y C puntos de un plano cartesiamo en 2 dimensiones, calculamos el area del triangulo:
Area = $\frac {Xa(Yc-Yb) + Xb(Ya-Yc) + Xc(Yb-Ya)}{2}$ 
```
- Se ingresan los planetas manualmente al crear la BD
- Pensando en escalabilidad (por ejemplo kubernetes), al iniciar la aplicación se valida el numero de pronosticos en la DB y si este numero es menor al número de dias que hay en 10 años (3600 dias), se hacen los calculos de los pronosticos y se guardan en la DB. Sino, no se hace este procedimiento.
- Para el Job se guardan fecha y número del último día, en cache local, al poblar la tabla pronosticos. Asi que, a la hora de la ejecución del job se valida que la fecha actual sea diferente a la de cache para hacer el calculo del dia. Nuevamente se hace esto pensando en escalabilidad de la aplicación. NOTA:  Se puede mejorar implementando una cache compartida como Elasticache.


#### **Stack utilizado:**
- Java 14
- Spring boot, spring webflux
- Amazon DynamoDB
- Amazon EC2

#### **Estructura del proyecto:**
- model -> Modelos utilizados para mapeos con base de datos y hacer calculos
- repository -> Configuración de conexión BD e interfaces con operaciones hacia la BD
- controller -> Exposición de API Rest, calculos de cada dia, manejo de errores
- job -> Scheduled de spring para calcular y guardar pronostico todos los dias a las 12 am
- util -> Clase y constantes utilitarias en todo el proyecto
- src/java/resources/database -> Script para crear y poblar tabla planetas por medio de AWS CLI
- src/java/resources/docker -> docker compose para crear contenedor docker de dynamo localmente
- src/test/java -> Pruebas unitarias
- src/test/resources -> Archivo jmx de Apache Jmeter para ejecutar pruebas de aceptación

#### **Modelo de base de datos:**
![Image not found](https://github.com/braduran/galaxy_weather/blob/main/images/DB_modelo.png?raw=true)

#### **Operaciones API rest:**
> GET http://ec2-18-232-180-203.compute-1.amazonaws.com:8080/clima/maxima-lluvia
```json
[
    {
        "clima": "tormenta",
        "dia": 3168
    },
    {
        "clima": "tormenta",
        "dia": 2268
    }
    ...
]
```

> GET http://ec2-18-232-180-203.compute-1.amazonaws.com:8080/clima?dia=566
```json
{
    "clima": "lluvia",
    "dia": 566
}
```

> GET http://ec2-18-232-180-203.compute-1.amazonaws.com:8080/periodos?clima=sequia
```json
{
    "clima": "sequia",
    "dias": 10
}
```

> GET http://ec2-18-232-180-203.compute-1.amazonaws.com:8080/periodos/todos
```json
[
    {
        "clima": "sequia",
        "dias": 10
    },
    {
        "clima": "optimo",
        "dias": 0
    }
    ...
]
```

#### **Resultador pruebas de aceptación:**
- Pruebas de aceptación locales

![Image not found](https://github.com/braduran/galaxy_weather/blob/main/images/Pruebas_aceptación_local.png?raw=true)

- Pruebas de aceptación en nube

![Image not found](https://github.com/braduran/galaxy_weather/blob/main/images/Pruebas_aceptacion_aws.png?raw=true)

#### **Referencias:**
- Movimiento circular uniforme: https://www.youtube.com/watch?v=OrSeFM4eXpU&ab_channel=Matem%C3%B3vil
- Area de un triangulo conociendo sus puntos: https://www.youtube.com/watch?v=Kpxphsvtqjo
- Spring data dynamodb: https://github.com/derjust/spring-data-dynamodb
- Spring boot scheduling: https://www.tutorialspoint.com/spring_boot/spring_boot_scheduling.htm
- Manejo de errores en un spring controller: https://programmerclick.com/article/1324151277/
- Despliegue jar en EC2: (https://stackoverflow.com/questions/11388014/using-scp-to-copy-a-file-to-amazon-ec2-instance) (https://medium.com/@calmarianet/3-formas-de-ejecutar-comandos-en-segundo-plano-en-linux-9dedb779ca7d#:~:text=%26%20%2B%20bg%20%2B%20fg,se%20ejecutar%C3%A1%20en%20segundo%20plano.)