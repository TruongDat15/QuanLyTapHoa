@echo off
REM Script to run APIGateWay using mvnw on Windows (cmd.exe)
cd /d %~dp0services\APIGateWay
:: use call so script returns control when mvnw exits
call mvnw.cmd spring-boot:run

cd /d D:\QuanLiTapHoa
start "Auth" cmd /k "run-auth.cmd"
start "Inventory" cmd /k "run-inventory.cmd"
start "Gateway" cmd /k "run-apigateway.cmd"