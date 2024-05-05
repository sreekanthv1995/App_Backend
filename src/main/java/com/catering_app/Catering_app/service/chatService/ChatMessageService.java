package com.catering_app.Catering_app.service.chatService;

import com.catering_app.Catering_app.dto.MessageReq;
import com.catering_app.Catering_app.model.ChatRoom;
import com.catering_app.Catering_app.model.Message;
import com.catering_app.Catering_app.repository.ChatRoomRepository;
import com.catering_app.Catering_app.repository.MessageRepository;
import com.catering_app.Catering_app.repository.UserRepository;
import com.sun.jdi.event.ExceptionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;

    public Message sendMessage(MessageReq message) {
        Optional<ChatRoom> optionalChatRoom = chatRoomService.getChatRoom(message.getChatRoomName());
        if (optionalChatRoom.isPresent()) {
            ChatRoom chatRoom = optionalChatRoom.get();
            return messageRepository.save(Message.builder()
                    .chatRoom(chatRoom)
                    .content(message.getContent())
                    .senderId(message.getSenderId())
                    .timeStamp(generateTimeStamp())
                    .build());
        } else {
            return null;
        }

    }

//    public Message sendMessage(MessageReq message) {
//        System.out.println(message.getType()+"  type");
//        Optional<ChatRoom> optionalChatRoom = chatRoomService.getChatRoom(message.getChatRoomName());
//        if (optionalChatRoom.isPresent()) {
//            ChatRoom chatRoom = optionalChatRoom.get();
//            if ("file".equals(message.getType())){
//                byte[] fileData = message.getContent().getBytes(StandardCharsets.UTF_8);
//                return messageRepository.save(Message.builder()
//                                .chatRoom(chatRoom)
//                                .senderId(message.getSenderId())
//                                .timeStamp(generateTimeStamp())
//                                .fileData(fileData)
//                                .fileType("image")
//                                .fileName("image.jpg")
//                        .build());
//            }else {
//                return messageRepository.save(Message.builder()
//                        .chatRoom(chatRoom)
//                        .content(message.getContent())
//                        .senderId(message.getSenderId())
//                        .timeStamp(generateTimeStamp())
//                        .build());
//            }
//        } else {
//            return null;
//        }
//
//    }

    private String generateTimeStamp() {
        Instant i = Instant.now();
        String date = i.toString();
        int endRange = date.indexOf('T');
        date = date.substring(0, endRange);
        date = date.replace('-', '/');
        String time = Integer.toString(i.atZone(ZoneOffset.UTC).getHour() + 1);
        time += ":";
        int minutes = i.atZone(ZoneOffset.UTC).getMinute();
        if (minutes > 9) {
            time += Integer.toString(minutes);
        } else {
            time += "0" + Integer.toString(minutes);
        }
        return date + "-" + time;
    }

    public List<Message> findChatRoom(String chatRoomName) {
        synchronized (this) {
            Optional<ChatRoom> optionalChatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
            if (optionalChatRoom.isEmpty()) {
                    chatRoomService.createChatRoom(chatRoomName);
                    return new ArrayList<>();
            } else {
                return messageRepository.findByChatRoomId(optionalChatRoom.get().getId());
            }
        }
    }

}