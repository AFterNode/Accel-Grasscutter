package cn.afternode.accel.testplugin;

import cn.afternode.accel.plugin.AccelPlugin;
import cn.afternode.accel.plugin.LoadAccelPlugin;

@LoadAccelPlugin
public class AccelTest extends AccelPlugin {
    public AccelTest() {
        super("AccelTest");
    }

    @Override
    public void init() {
        System.out.println("Test");
    }
}
