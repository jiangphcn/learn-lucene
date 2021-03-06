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
 *
 * BlockTree词项词典.
 *
 * <p>
 * 该词项词典，将所有的词项以块组织，可以共享前缀，然后在内存中存储为前缀特里树（同类算法：FST）。
 *
 * 通过实现扩展点，可以扩展自己的{@link org.apache.lucene.codecs.PostingsWriterBase}
 * </p>
 *
 * <p>
 *     关于文件格式，请参考: {@link org.apache.lucene.codecs.blocktree.BlockTreeTermsWriter}
 * </p>
 */
package org.apache.lucene.codecs.blocktree;
