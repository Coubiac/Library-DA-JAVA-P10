FROM mysql:5.7
ADD library.sql /docker-entrypoint-initdb.d
ADD library_users.sql /docker-entrypoint-initdb.d
ADD library_emprunts.sql /docker-entrypoint-initdb.d
EXPOSE 3306

