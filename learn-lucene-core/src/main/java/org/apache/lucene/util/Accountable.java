/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.util;


import java.util.Collection;
import java.util.Collections;

/**
 * 可以计算对象占用内存(RAM)大小
 *
 * @lucene.internal
 */
public interface Accountable {

  /**
   * 返回该对象占用内存的字节数.负值是非法的.
   */
  long ramBytesUsed();

  /**
   * 返回内嵌资源.
   * 该结果应该是某一时间点的快照(为了避免竞态条件[Race Conditions])
   *
   * @see Accountables
   */
  default Collection<Accountable> getChildResources() {
    return Collections.emptyList();
  }

}
