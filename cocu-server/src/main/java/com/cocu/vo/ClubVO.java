package com.cocu.vo;

import com.cocu.entity.Club;
import com.cocu.entity.ClubType;
import com.cocu.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubVO extends Club implements Serializable {

    private static final long serialVersionUID = 1L;

    // club creator email
    public String creator;

    // club president email
    public String president;

    public String presidentName;

    public String type;

}
