package com.project.instagram.domain.friend;

import com.project.instagram.domain.time.BaseTimeEntity;
import com.project.instagram.domain.user.User;
import com.project.instagram.domain.user.UserDetail;
import com.project.instagram.web.dto.friend.ReadFollowListResponseDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@SqlResultSetMapping(
        name = "followingListMapping",
        classes = @ConstructorResult(
                targetClass = ReadFollowListResponseDto.class,
                columns = {
                        @ColumnResult(name = "followCode", type = Integer.class),
                        @ColumnResult(name = "fromUserCode", type = Integer.class),
                        @ColumnResult(name = "followingFlag", type = Integer.class),
                        @ColumnResult(name = "fromUserName", type = String.class),
                        @ColumnResult(name = "toUserName", type = String.class),
                        @ColumnResult(name = "toDetailCode", type = Integer.class),
                        @ColumnResult(name = "fromDetailCode", type = Integer.class),
                        @ColumnResult(name = "toUserId", type = String.class),
                        @ColumnResult(name = "fromUserId", type = String.class),
                        @ColumnResult(name = "toUserNickname", type = String.class),
                        @ColumnResult(name = "fromUserNickname", type = String.class)
                }
        )
)
@NamedNativeQuery(
        name = "findFollowingListByUserCode",
        query = "select" +
                "   f.follow_code as followCode," +
//                "   f.create_date," +
//                "   f.update_date," +
                "   f.from_user_code as fromUserCode," +
//                "   f.to_user_code," +
                "   um.detail_code as toDetailCode," +
//                "   um.user_email as user_email1," +
                "   um.user_id as toUserId," +
                "   um.user_name as toUserName," +
                "   um.user_nickname as toUserNickname," +
//                "   um.user_password as user_password1," +
                "   um2.detail_code as fromDetailCode," +
//                "   um2.user_email as user_email2," +
                "   um2.user_id as fromUserId," +
                "   um2.user_name as fromUserName," +
                "   um2.user_nickname as fromUserNickname," +
//                "   um2.user_password as user_password2," +
                "   if(following_table.to_user_code is not null, 1, 0) as followingFlag " +
//                "   um2.user_name as from_user_name," +
//                "   um.user_name as to_user_name " +
                "from" +
                "   follow f" +
                "   left outer join (select" +
                "   to_user_code" +
                "   from" +
                "   follow" +
                "   where" +
                "   from_user_code = :toUserCode) following_table on(following_table.to_user_code = f.from_user_code)" +
                "inner join user_mst um on(um.user_code = f.to_user_code)" +
                "inner join user_mst um2 on(um2.user_code = f.from_user_code)" +
//                "inner join user_dtl ud on(ud.user_code = um.user_code)" +
//                "inner join user_dtl ud2 on(ud2.user_code = um2.user_code)" +
                "where" +
                "   f.to_user_code = :toUserCode",
        resultSetMapping = "followingListMapping"
)

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
//@ToString
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followCode;

    @ManyToOne
    @JoinColumn(name = "toUserCode")
    private User toUser;

    @ManyToOne
    @JoinColumn(name = "fromUserCode")
    private User fromUser;

    @Transient
    private int following_flag;
    @Transient
    private LocalDateTime create_date;
    @Transient
    private LocalDateTime update_date;
}