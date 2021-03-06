/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.core.persistence.impl.nullpm;

import org.apache.activemq.api.core.ActiveMQBuffers;
import org.apache.activemq.core.journal.SequentialFile;
import org.apache.activemq.core.server.LargeServerMessage;
import org.apache.activemq.core.server.impl.ServerMessageImpl;

class NullStorageLargeServerMessage extends ServerMessageImpl implements LargeServerMessage
{

   public NullStorageLargeServerMessage()
   {
      super();
   }

   @Override
   public void releaseResources()
   {
   }

   @Override
   public synchronized void addBytes(final byte[] bytes)
   {
      if (buffer == null)
      {
         buffer = ActiveMQBuffers.dynamicBuffer(bytes.length);
      }

      // expand the buffer
      buffer.writeBytes(bytes);
   }

   @Override
   public void deleteFile() throws Exception
   {
      // nothing to be done here.. we don really have a file on this Storage
   }

   @Override
   public boolean isLargeMessage()
   {
      return true;
   }

   @Override
   public void decrementDelayDeletionCount()
   {

   }

   @Override
   public void incrementDelayDeletionCount()
   {

   }

   @Override
   public synchronized int getEncodeSize()
   {
      return getHeadersAndPropertiesEncodeSize();
   }

   @Override
   public String toString()
   {
      return "LargeServerMessage[messageID=" + messageID + ", durable=" + durable + ", address=" + getAddress()  + ",properties=" + properties.toString() + "]";
   }

   @Override
   public void setPaged()
   {
   }

   @Override
   public void setPendingRecordID(long pendingRecordID)
   {
   }

   @Override
   public long getPendingRecordID()
   {
      return -1;
   }


   @Override
   public SequentialFile getFile()
   {
      return null;
   }

}
