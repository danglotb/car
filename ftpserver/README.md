# ftpserver

filesys 
=========
is the file system used by the ftp server

the file .account contains tuples (login:password) to build a map in the server.


java class
==========

*DefConstant : contain the whole constant used by the server
*Server : class wich run a FtpRequest (in a thread) for each client connected
*FtpRequest : class wich is processing request and response with the client
