# QuestLima

![https://imgur.com/sixbkEn.png](https://imgur.com/sixbkEn.png)

Редактор текстовых квестов на tomcat и postgresql.
Позволяет создавать квесты, редактировать их и собственно играть.
Редактор квестов - ![направленный ацикличный граф](https://en.wikipedia.org/wiki/Directed_acyclic_graph) (на базе обычного ![дерева](https://observablehq.com/@d3/tree) от Mike Bostock и ![d3.js](https://d3js.org/).

Для запуска нужно CREATE DATABASE quest; в postgres (данные для подключения к БД в application.properties).
Затем запустить script.sql.

Крутиться должно на tomcat не ниже 10 версии (из за javax -> jakarta), который Вам нужно подготовить самостоятельно.
