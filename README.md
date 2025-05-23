Bienvenido al Sistema administrador de "El Papalote Taco & Grill"

Para poder ejecutarse, se tiene que cumplir lo siguiente:

Cumplir con la siguiente base de datos creada: 

    spring.datasource.url=jdbc:mysql://localhost:3306/Papalote
    spring.datasource.username=root
    spring.datasource.password=2eI0t#ePSd

Ejecutar primero el PapaloteDBmanager
Ejecutar el PapaloteAdmin luego de 2 a 5 min

Luego de eso el programa funcionará correctamente.

Los ejecutables se encuentran en los target:
    PIAPOO\PapaloteDBManager\target,
    
    PIAPOO\PapaloteAdmin\target\gluonfx\x86_64-windows

PapaloteDBManager es el administrador de la base de datos, por lo que solo deberá abrirse antes de empezar a utilizar PapaloteAdmin

PapaloteAdmin permite administrar los usuarios, productos, inventario, ordenes y ventas.

El order de ejecución es el siguiente:
Login>Main>categorias>Usuarios>Productos>Inventarios>Tomar ordenes>Mostrar ventas y detalle de venta
