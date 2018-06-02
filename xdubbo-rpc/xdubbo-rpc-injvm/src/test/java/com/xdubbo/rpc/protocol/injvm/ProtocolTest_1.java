package com.xdubbo.rpc.protocol.injvm;

import com.xdubbo.common.extension.ExtensionLoader;
import com.xdubbo.common.extension.SPI;
import com.xdubbo.rpc.Filter;
import com.xdubbo.rpc.Protocol;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Created by luozhonghua on 2018/5/26.
 */
public class ProtocolTest_1 {
    @Test
    public void test_destroyWontCloseAllProtocol() throws Exception {
       // Protocol autowireProtocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
         Protocol InjvmProtocol = ExtensionLoader.getExtensionLoader(Protocol.class).getExtension("mock");
      //  Filter InjvmProtocol = ExtensionLoader.getExtensionLoader(com.xdubbo.rpc.Filter.class).getAdaptiveExtension();
          //  System.out.println(SPI.class.getSimpleName());
        try {
            InjvmProtocol.destroy();
            // autowireProtocol.destroy();
        } catch (UnsupportedOperationException expected) {
            assertThat(expected.getMessage(), containsString("of interface com.xdubbo.rpc.Protocol is not adaptive method!"));
        }
    }
}
