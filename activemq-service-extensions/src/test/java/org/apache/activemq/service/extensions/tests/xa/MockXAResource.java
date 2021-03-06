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
package org.apache.activemq.service.extensions.tests.xa;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class MockXAResource implements XAResource
{
   @Override
   public void commit(Xid xid, boolean b) throws XAException
   {

   }

   @Override
   public void end(Xid xid, int i) throws XAException
   {

   }

   @Override
   public void forget(Xid xid) throws XAException
   {

   }

   @Override
   public int getTransactionTimeout() throws XAException
   {
      return 0;
   }

   @Override
   public boolean isSameRM(XAResource xaResource) throws XAException
   {
      return false;
   }

   @Override
   public int prepare(Xid xid) throws XAException
   {
      return 0;
   }

   @Override
   public Xid[] recover(int i) throws XAException
   {
      return new Xid[0];
   }

   @Override
   public void rollback(Xid xid) throws XAException
   {

   }

   @Override
   public boolean setTransactionTimeout(int i) throws XAException
   {
      return false;
   }

   @Override
   public void start(Xid xid, int i) throws XAException
   {

   }
}
