/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
package nextapp.echo.chart.webcontainer.sync.component;

import nextapp.echo.app.Component;
import nextapp.echo.app.util.Context;
import nextapp.echo.chart.app.ChartDisplay;
import nextapp.echo.chart.webcontainer.service.ChartImageService;
import nextapp.echo.webcontainer.AbstractComponentSynchronizePeer;
import nextapp.echo.webcontainer.ServerMessage;
import nextapp.echo.webcontainer.Service;
import nextapp.echo.webcontainer.WebContainerServlet;
import nextapp.echo.webcontainer.service.JavaScriptService;

public class ChartDisplayPeer
        extends AbstractComponentSynchronizePeer {

	public static final int DEFAULT_WIDTH = 200;
	public static final int DEFAULT_HEIGHT = 200;
	
    protected static final Service DATE_FIELD_SERVICE = JavaScriptService.forResource("Echo.Chart.ChartDisplay",
            "nextapp/echo/chart/webcontainer/resource/js/Ext20.DateField.js");
    
    private static final String PROPERTY_URI = "uri";


    static {
        WebContainerServlet.getServiceRegistry().add(DATE_FIELD_SERVICE);
    }

    public ChartDisplayPeer() {
        super();
        addOutputProperty(PROPERTY_URI);
    }

    public Class getComponentClass() {
        return ChartDisplay.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "ECD" : "EchoChartDisplay";
    }
    
    public Object getOutputProperty(Context context, Component component, String propertyName, int propertyIndex) {
        Object ret = null;
        
        ChartDisplay chartDisplay = (ChartDisplay) component;
        
        if (propertyName.equals(PROPERTY_URI)) {
            return ChartImageService.getInstance().createUri(WebContainerServlet.getActiveConnection().getUserInstance(), chartDisplay);
        }
        else {
            ret = super.getOutputProperty(context, component, propertyName, propertyIndex);
        }
        
        return ret;
    }

    /**
     * @see nextapp.echo.webcontainer.ComponentSynchronizePeer#init(Context)
     */
    public void init(Context context, Component c) {
        super.init(context, c);
      ServerMessage serverMessage = (ServerMessage) context.get(ServerMessage.class);
      serverMessage.addLibrary(DATE_FIELD_SERVICE.getId());
    }
}
