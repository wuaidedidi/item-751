#!/usr/bin/env bash
set -euo pipefail

echo "[ems] Starting MySQL on port 3306"
mysqld_safe --datadir=/var/lib/mysql --socket=/var/run/mysqld/mysqld.sock > /tmp/mysql.log 2>&1 &

for _ in $(seq 1 60); do
  if mysqladmin ping --protocol=socket -uroot --socket=/var/run/mysqld/mysqld.sock >/dev/null 2>&1 || mysqladmin ping -uroot -proot123 >/dev/null 2>&1; then
    break
  fi
  sleep 1
done

if mysql --protocol=socket -uroot --socket=/var/run/mysqld/mysqld.sock -e "SELECT 1" >/dev/null 2>&1; then
  mysql --protocol=socket -uroot --socket=/var/run/mysqld/mysqld.sock < /app/database/runtime-init.sql
fi

mysql -uroot -proot123 ems_db < /app/database/init.sql

echo "[ems] Starting Spring Boot backend on port 8000"
cd /tmp/ems-runtime
java -jar /opt/ems/app.jar > /tmp/backend.log 2>&1 &

for _ in $(seq 1 90); do
  if curl -fsS http://127.0.0.1:8000/api/dashboard >/dev/null 2>&1 || grep -q "Started EmsApplication" /tmp/backend.log; then
    break
  fi
  sleep 1
done

cat /tmp/backend.log
echo "[ems] Starting Nginx on port 80"
echo "[ems] Frontend: http://localhost:3000  Backend API: http://localhost:8000"
exec nginx -g "daemon off;"
