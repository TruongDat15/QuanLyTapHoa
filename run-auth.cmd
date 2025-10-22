@echo off
REM Script to run AuthService using mvnw on Windows (cmd.exe)
cd /d %~dp0services\AuthService
:: use call so script returns control when mvnw exits
call mvnw.cmd spring-boot:run

