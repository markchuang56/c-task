package org.bluetooth.time;

//import com.roche.acconnect.application.domainmodel.Node;
//import com.roche.acconnect.datadevice.provider.Table_MeterDeltas.MeterDeltasColumns;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;

//public class DeltaNode extends Node {
public class DeltaNode {
    public DeltaNode() {
        //this.children = new ArrayList();
    }
/*
    public DeltaNode(com.roche.acconnect.application.domainmodel.time.TimeDelta timeDelta) {
        this.children = new ArrayList();
        this.data = new com.roche.acconnect.application.domainmodel.time.TimeDelta(timeDelta);
    }

    public static void attachOtherRoot(DeltaNode deltaNode, DeltaNode deltaNode2, com.roche.acconnect.application.domainmodel.time.TimeDelta timeDelta) {
        setRootData(deltaNode2, timeDelta);
        deltaNode.addSubtree(deltaNode2);
    }

    public static long getCumulativeDelta(DeltaNode deltaNode) {
        if (deltaNode.getParent() == null) {
            return deltaNode.getDelta();
        }
        return getCumulativeDelta((DeltaNode) deltaNode.getParent()) + deltaNode.getDelta();
    }
*/
    private Long getDeltaFromJSON(JSONObject jSONObject) {
        try {
            return Long.valueOf(jSONObject.getLong("delta"));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
/*
    private int getIdFromJSON(JSONObject jSONObject) {
        try {
            return jSONObject.getInt(Name.MARK);
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private String getKeyFromJSON(JSONObject jSONObject) {
        try {
            return jSONObject.getString(MeterDeltasColumns.KEY);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void setRootData(DeltaNode deltaNode, TimeDelta timeDelta) {
        deltaNode.setData(timeDelta);
    }

    public static void updateNodeDeltaAndSubtree(DeltaNode deltaNode, TimeDelta timeDelta) {
        setRootData(deltaNode, timeDelta);
        deltaNode.flattenSubtree();
    }

    protected JSONObject convertDataToJSON() {
        TimeDelta timeDelta = (TimeDelta) getData();
        JSONObject convertDataToJSON = super.convertDataToJSON();
        try {
            convertDataToJSON.put(Name.MARK, timeDelta.getId());
            convertDataToJSON.put(MeterDeltasColumns.KEY, timeDelta.getKey());
            convertDataToJSON.put("delta", timeDelta.getDelta());
            return convertDataToJSON;
        } catch (JSONException e) {
            e.printStackTrace();
            return convertDataToJSON;
        }
    }

    protected void convertJSONToData(JSONObject jSONObject) {
        super.convertJSONToData(jSONObject);
        if (jSONObject != null) {
            TimeDelta timeDelta = new TimeDelta(getDeltaFromJSON(jSONObject), getKeyFromJSON(jSONObject));
            timeDelta.setId(getIdFromJSON(jSONObject));
            setData(timeDelta);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return this.data == null ? node.getData() == null : getKey().equals(((DeltaNode) node).getKey());
    }

    public DeltaNode findKey(String str) {
        return (DeltaNode) treeSearch(new DeltaNode(new TimeDelta(str)));
    }

    public void flattenDownStackRecursiveHook(Node node) {
        ((DeltaNode) node).setDelta(((DeltaNode) node).getDelta() + ((DeltaNode) node.getParent()).getDelta());
        super.flattenDownStackRecursiveHook(node);
    }

    public long getDelta() {
        return ((TimeDelta) getData()).getDelta().longValue();
    }

    public String getKey() {
        return ((TimeDelta) getData()).getKey();
    }

    public TimeDelta setData(TimeDelta timeDelta) {
        return (TimeDelta) super.setData(new TimeDelta(timeDelta));
    }

    public void setDelta(long j) {
        TimeDelta timeDelta = new TimeDelta((TimeDelta) getData());
        timeDelta.setDelta(Long.valueOf(j));
        setData(timeDelta);
    }
    */
}
