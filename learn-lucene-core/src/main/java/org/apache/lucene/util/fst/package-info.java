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

/**
 * 有穷状态转换器
 * <p>
 * 该包实现了一个<a href="http://en.wikipedia.org/wiki/Finite_state_transducer">
 * 有穷状态转换器</a>。包含了以下特性:
 * <ul>
 *    <li>快速、低内存开销构建一个最小FST.(输入必须是有序的)</li>
 *    <li>低对象开销并且快速反序列化(通过byte数组体现)</li>
 *    <li>{@link org.apache.lucene.util.fst.Util#getByOutput 通过输出查找}当输出有序时(例如，序数和文件指针)。
 *    <li>可插拔的{@link org.apache.lucene.util.fst.Outputs 输出组件}</li>
 *    <li>{@link org.apache.lucene.util.fst.Util#shortestPaths N最短路径} 通过权重搜索</li>
 *    <li>({@link org.apache.lucene.util.fst.IntsRefFSTEnum IntsRef} 和 {@link org.apache.lucene.util.fst.BytesRefFSTEnum BytesRef}) 两个枚举器，行为类似于{@link java.util.SortedMap SortedMap} 迭代器。
 *    <li>可选的two-pass压缩(本版本移除该项)</li>
 * </ul>
 * <p>
 * 构建FST的示例:
 * <pre class="prettyprint">
 *     // Input values (keys). These must be provided to Builder in Unicode sorted order!
 *     String inputValues[] = {"cat", "dog", "dogs"};
 *     long outputValues[] = {5, 7, 12};
 *     
 *     PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
 *     Builder&lt;Long&gt; builder = new Builder&lt;Long&gt;(INPUT_TYPE.BYTE1, outputs);
 *     BytesRef scratchBytes = new BytesRef();
 *     IntsRefBuilder scratchInts = new IntsRefBuilder();
 *     for (int i = 0; i &lt; inputValues.length; i++) {
 *       scratchBytes.copyChars(inputValues[i]);
 *       builder.add(Util.toIntsRef(scratchBytes, scratchInts), outputValues[i]);
 *     }
 *     FST&lt;Long&gt; fst = builder.finish();
 * </pre>
 * Retrieval by key:
 * <pre class="prettyprint">
 *     Long value = Util.get(fst, new BytesRef("dog"));
 *     System.out.println(value); // 7
 * </pre>
 * 通过值来检索:
 * <pre class="prettyprint">
 *     // Only works because outputs are also in sorted order
 *     IntsRef key = Util.getByOutput(fst, 12);
 *     System.out.println(Util.toBytesRef(key, scratchBytes).utf8ToString()); // dogs
 * </pre>
 * Iterate over key-value pairs in sorted order:
 * <pre class="prettyprint">
 *     // Like TermsEnum, this also supports seeking (advance)
 *     BytesRefFSTEnum&lt;Long&gt; iterator = new BytesRefFSTEnum&lt;Long&gt;(fst);
 *     while (iterator.next() != null) {
 *       InputOutput&lt;Long&gt; mapEntry = iterator.current();
 *       System.out.println(mapEntry.input.utf8ToString());
 *       System.out.println(mapEntry.output);
 *     }
 * </pre>
 * 通过权重的N最短路径:
 * <pre class="prettyprint">
 *     Comparator&lt;Long&gt; comparator = new Comparator&lt;Long&gt;() {
 *       public int compare(Long left, Long right) {
 *         return left.compareTo(right);
 *       }
 *     };
 *     Arc&lt;Long&gt; firstArc = fst.getFirstArc(new Arc&lt;Long&gt;());
 *     MinResult&lt;Long&gt; paths[] = Util.shortestPaths(fst, firstArc, comparator, 2);
 *     System.out.println(Util.toBytesRef(paths[0].input, scratchBytes).utf8ToString()); // cat
 *     System.out.println(paths[0].output); // 5
 *     System.out.println(Util.toBytesRef(paths[1].input, scratchBytes).utf8ToString()); // dog
 *     System.out.println(paths[1].output); // 7
 * </pre>
 */
package org.apache.lucene.util.fst;
