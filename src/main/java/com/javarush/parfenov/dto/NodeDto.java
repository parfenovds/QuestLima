package com.javarush.parfenov.dto;

import com.javarush.parfenov.entity.NodeType;
import lombok.Builder;
import lombok.Value;

import javax.swing.*;

@Value
@Builder
public class NodeDto {
    String nodeId;
    String shortName;
    String text;
    String parentId;
    String questId;
    String type;
    String nextLonelyId;
}
