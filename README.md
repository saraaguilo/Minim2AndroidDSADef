# Minim2AndroidDSADef
Tareas
Nueva funcionalidad que permita realizar una consulta sobre la aplicación.
● T1. En la App Android, añadir una nueva actividad que proporcione un pequeño
formulario para solicitar y enviar información sobre un tema
● T2. Nueva ruta en el backend que reciba la nueva consulta

Primero he editado Android. He creado un question_activity.xml. He puesto la posibilidad de poner un título (para la pregunta), un TextInputEditText para poner la pregunta correspondiente, y un botón para enviar la petición.
También he creado una clase Question. En el MainActivity, le he tenido que añadir una función llamada AskQuestion. En la Api también he generado los cambios pertinentes (también en el AndroidManifest).

A partir de ahí, he cambiado algunas cosas en el Backend. He generado el model Question, y he cambiado el GameManager y el GameManagerImpl. 
