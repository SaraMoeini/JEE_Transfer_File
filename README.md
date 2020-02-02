# JEE_Transfer_File

1) *.jsp:
in form include a input type="file"
in balise form include enctype="multipart/form-data">

2) web.xml:
in balise servlet, we add <multipart-config> as you see:
<servlet>
        <servlet-name>Test</servlet-name>
        <servlet-class>com.octest.servlets.Test</servlet-class>
        <multipart-config>
            <location>/Users/mateo21/fichierstmp/</location> <!-- Where to save big files temporarely -->
            <max-file-size>10485760</max-file-size> <!-- 10 Mo -->
            <max-request-size>52428800</max-request-size> <!-- 5 x 10 Mo -->
            <file-size-threshold>1048576</file-size-threshold> <!-- 1 Mo -->
        </multipart-config>
</servlet>

3) Servlet :
Add 2 constant, first we define a buffer (tempon) in order to copy files from temporary document into final doc.
  public static final int TAILLE_TAMPON = 10240;
  public static final String CHEMIN_FICHIERS = "/Users/mateo21/fichiers/"; // Where to save finally the file
  
An object of "Part" 
    Part part= request.getPart("file")
 
Get file name with a methode written mannually by us which is dedicated to this and called getNomFichier()

...
