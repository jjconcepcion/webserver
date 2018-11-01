# Web Server

A simple web server written in Java.

## Demo

Clone the repository.

    $ git clone https://github.com/jjconcepcion/webserver.git

Edit the configuration files: [httpd.conf](webserver/conf/httpd.conf) and [.htaccess](webserver/public_html/.htaccess) and replace each instance of */PATH-TO/* to reflect the absolute paths of their respective files

Inside the main webserver directory compile and run the web server.

    $ javac ServerStarter.java
    $ java ServerStarter


Open a browser to

    http://localhost:9999

Authenticate using the folowing credentials:

    username: myuser
    password: password