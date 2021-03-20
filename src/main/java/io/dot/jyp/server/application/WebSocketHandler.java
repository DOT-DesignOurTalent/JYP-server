package io.dot.jyp.server.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dot.jyp.server.domain.Group;
import io.dot.jyp.server.domain.GroupClient;
import io.dot.jyp.server.domain.GroupMessage;
import io.dot.jyp.server.domain.GroupMessage.MessageType;
import io.dot.jyp.server.domain.GroupRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
  List<HashMap<String, Object>> groupList = new ArrayList<>();
  ObjectMapper objectMapper = new ObjectMapper();
  GroupRepository groupRepository;
  GroupClient groupClient;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String msg = message.getPayload();
    GroupMessage groupMessage = objectMapper.readValue(msg,GroupMessage.class);

    String groupCode = groupMessage.getCode();
    Group group = groupRepository.findGroupByCodeOrElseThrow(groupCode);

    GroupMessage newGroupMessage = group.handleGroupMessage(groupMessage);
    HashMap<String, Object> tempGroup =groupList.stream()
        .filter(grp->grp.get("code")==groupCode)
        .findFirst()
        .orElseThrow(() ->
            new IllegalArgumentException(String.format("There is no right group")));
    tempGroup.remove("code");
    tempGroup.values()
        .stream()
        .forEach(sess -> groupClient.sendMessage((WebSocketSession) sess ,message));
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    super.afterConnectionEstablished(session);

    boolean flag = false;
    String url = session.getUri().toString();
    String groupCode = url.split("/ws/group/")[1];
    int idx = groupList.size();
    for(int i=0; i<groupList.size(); i++) {
      String tempGroupCode = (String) groupList.get(i).get("code");
      if(tempGroupCode.equals(groupCode)) {
        flag = true;
        idx = i;
        break;
      }
    }
    HashMap<String, Object> map;
    if(flag) {
      map = groupList.get(idx);
      map.put(session.getId(), session);
    }
    else {
      map = new HashMap<String, Object>();
      map.put("code", groupCode);
      map.put(session.getId(), session);
      groupList.add(map);
    }

    if(map.size()>6){
      //error;
    }

    GroupMessage groupMessage=new GroupMessage(
        MessageType.ENTER,
        groupCode,
        map.size()-1,
        ""
    );
    String groupMessageString = groupMessage.toString();
    session.sendMessage(new TextMessage(groupMessageString));
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    //소켓 종료
    for(int i=0; i<groupList.size(); i++) {
      groupList.get(i).remove(session.getId());
    }
    super.afterConnectionClosed(session, status);
  }
}
