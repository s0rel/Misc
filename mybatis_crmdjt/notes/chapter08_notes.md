MyBatis 允许在已映射语句执行过程中的某一点进行拦截调用。默认情况下，MyBatis 允许使用插件来拦截的接口和方法有以下几个：

* Executor(update、query、flushStatements、commit、rollback、getTransaction、close、isClosed)
* ParameterHandler(getParameterObject、setParameters)
* ResultSetHandler(handleResultSets、handleCursorResultSets、handleOutputParameters)
* StatementHandler(prepare、parameterize、batch、update、query)

