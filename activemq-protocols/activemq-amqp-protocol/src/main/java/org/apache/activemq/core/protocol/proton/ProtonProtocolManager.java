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
package org.apache.activemq.core.protocol.proton;

import java.util.concurrent.Executor;

import io.netty.channel.ChannelPipeline;
import org.apache.activemq.api.core.ActiveMQBuffer;
import org.apache.activemq.api.core.client.ActiveMQClient;
import org.apache.activemq.core.protocol.proton.converter.ProtonMessageConverter;
import org.apache.activemq.core.protocol.proton.plug.ActiveMQProtonConnectionCallback;
import org.apache.activemq.core.remoting.impl.netty.NettyServerConnection;
import org.apache.activemq.core.server.ActiveMQServer;
import org.apache.activemq.core.server.management.Notification;
import org.apache.activemq.core.server.management.NotificationListener;
import org.apache.activemq.spi.core.protocol.ConnectionEntry;
import org.apache.activemq.spi.core.protocol.MessageConverter;
import org.apache.activemq.spi.core.protocol.ProtocolManager;
import org.apache.activemq.spi.core.protocol.RemotingConnection;
import org.apache.activemq.spi.core.remoting.Acceptor;
import org.apache.activemq.spi.core.remoting.Connection;
import org.proton.plug.AMQPServerConnectionContext;
import org.proton.plug.context.server.ProtonServerConnectionContextFactory;

/**
 * A proton protocol manager, basically reads the Proton Input and maps proton resources to ActiveMQ resources
 */
public class ProtonProtocolManager implements ProtocolManager, NotificationListener
{
   private final ActiveMQServer server;

   private MessageConverter protonConverter;

   public ProtonProtocolManager(ActiveMQServer server)
   {
      this.server = server;
      this.protonConverter = new ProtonMessageConverter(server.getStorageManager());
   }

   public ActiveMQServer getServer()
   {
      return server;
   }


   @Override
   public MessageConverter getConverter()
   {
      return protonConverter;
   }

   @Override
   public void onNotification(Notification notification)
   {

   }

   @Override
   public ConnectionEntry createConnectionEntry(Acceptor acceptorUsed, Connection remotingConnection)
   {
      ActiveMQProtonConnectionCallback connectionCallback = new ActiveMQProtonConnectionCallback(this, remotingConnection);

      AMQPServerConnectionContext amqpConnection = ProtonServerConnectionContextFactory.getFactory().createConnection(connectionCallback);

      Executor executor = server.getExecutorFactory().getExecutor();

      ActiveMQProtonRemotingConnection delegate = new ActiveMQProtonRemotingConnection(this, amqpConnection, remotingConnection, executor);

      connectionCallback.setProtonConnectionDelegate(delegate);

      ConnectionEntry entry = new ConnectionEntry(delegate, executor,
                                                  System.currentTimeMillis(), ActiveMQClient.DEFAULT_CONNECTION_TTL);

      return entry;
   }

   @Override
   public void removeHandler(String name)
   {

   }

   @Override
   public void handleBuffer(RemotingConnection connection, ActiveMQBuffer buffer)
   {
      ActiveMQProtonRemotingConnection protonConnection = (ActiveMQProtonRemotingConnection)connection;

      protonConnection.bufferReceived(protonConnection.getID(), buffer);
   }

   @Override
   public void addChannelHandlers(ChannelPipeline pipeline)
   {

   }

   @Override
   public boolean isProtocol(byte[] array)
   {
      return array.length >= 4 && array[0] == (byte) 'A' && array[1] == (byte) 'M' && array[2] == (byte) 'Q' && array[3] == (byte) 'P';
   }

   @Override
   public void handshake(NettyServerConnection connection, ActiveMQBuffer buffer)
   {
   }


}
