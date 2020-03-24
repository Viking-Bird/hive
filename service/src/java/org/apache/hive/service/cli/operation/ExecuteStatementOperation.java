/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hive.service.cli.operation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.hive.ql.processors.CommandProcessor;
import org.apache.hadoop.hive.ql.processors.CommandProcessorFactory;
import org.apache.hive.service.cli.HiveSQLException;
import org.apache.hive.service.cli.OperationType;
import org.apache.hive.service.cli.session.HiveSession;

public abstract class ExecuteStatementOperation extends Operation {
  protected String statement = null;
  protected Map<String, String> confOverlay = new HashMap<String, String>();

  public ExecuteStatementOperation(HiveSession parentSession, String statement,
      Map<String, String> confOverlay, boolean runInBackground) {
    super(parentSession, OperationType.EXECUTE_STATEMENT, runInBackground);
    this.statement = statement;
    setConfOverlay(confOverlay);
  }

  public String getStatement() {
    return statement;
  }

  /**
   * 创建具体的SQL操作对象
   * @param parentSession
   * @param statement
   * @param confOverlay
   * @param runAsync
   * @return SQLOperation或者HiveCommandOperation
   * @throws HiveSQLException
   */
  public static ExecuteStatementOperation newExecuteStatementOperation(
      HiveSession parentSession, String statement, Map<String, String> confOverlay, boolean runAsync)
          throws HiveSQLException {
    // 解析command命令，获取command命令处理器
    String[] tokens = statement.trim().split("\\s+");
    CommandProcessor processor = null;
    try {
      processor = CommandProcessorFactory.getForHiveCommand(tokens, parentSession.getHiveConf());
    } catch (SQLException e) {
      throw new HiveSQLException(e.getMessage(), e.getSQLState(), e);
    }
    // 如果command命令处理器为空，则创建一个SQL操作对象(SQLOperation)，否则创建一个Hive命令操作对象(HiveCommandOperation)
    if (processor == null) {
      return new SQLOperation(parentSession, statement, confOverlay, runAsync);
    }
    return new HiveCommandOperation(parentSession, statement, processor, confOverlay);
  }

  protected Map<String, String> getConfOverlay() {
    return confOverlay;
  }

  protected void setConfOverlay(Map<String, String> confOverlay) {
    if (confOverlay != null) {
      this.confOverlay = confOverlay;
    }
  }
}
