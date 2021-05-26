/** 以下代码由代码生成器自动生成，如无必要，请勿修改！2021-5-19 11:09:12 **/
package cn.chinaunicom.person.model.query;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.math.BigDecimal;
import cn.chinaunicom.person.bean.PageQueryModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
public class GuestInfoQueryModel extends PageQueryModel {
	@ApiModelProperty(value = "自增主键", name = "id")
	private Long id;


	@ApiModelProperty(value = "来访申请id", name = "apply_id")
	private Long applyId;


	@ApiModelProperty(value = "来访人姓名", name = "guest_name")
	private String guestName;


	@ApiModelProperty(value = "来访人身份证号", name = "guest_idcard")
	private String guestIdcard;


	@ApiModelProperty(value = "来访人证件类型：0身份证", name = "guest_idtype")
	private Integer guestIdtype;


	@ApiModelProperty(value = "性别: 1表示男性 2表示女性 0表示未设置", name = "guest_gender")
	private Integer guestGender;


	@ApiModelProperty(value = "来访人手机号", name = "guest_mobile")
	private String guestMobile;


	@ApiModelProperty(value = "二维码", name = "guest_qrcode")
	private String guestQrcode;


	@ApiModelProperty(value = "出生日期开始日期时间", name = "guest_birthday", notes = "TimeFrom")
	private LocalDateTime guestBirthdayFrom;

	@ApiModelProperty(value = "出生日期结束日期时间", name = "guest_birthday", notes = "TimeTo")
	private LocalDateTime guestBirthdayTo;

	@ApiModelProperty(value = "出生日期", name = "guest_birthday")
	private LocalDateTime guestBirthday;


	@ApiModelProperty(value = "来访人照片", name = "guest_photo_url")
	private String guestPhotoUrl;


	@ApiModelProperty(value = "来访人权限名称", name = "guest_seqname")
	private String guestSeqname;


	@ApiModelProperty(value = "来访人权限", name = "guest_seqid")
	private String guestSeqid;


	@ApiModelProperty(value = "备注", name = "guest_remarkt")
	private String guestRemarkt;


	@ApiModelProperty(value = "受访人姓名", name = "interviewee_name")
	private String intervieweeName;


	@ApiModelProperty(value = "受访人编码", name = "interviewee_code")
	private String intervieweeCode;


	@ApiModelProperty(value = "受访人电话", name = "interviewee_mobile")
	private String intervieweeMobile;


	@ApiModelProperty(value = "访问开始时间开始日期时间", name = "guest_startTime", notes = "TimeFrom")
	private LocalDateTime guestStartTimeFrom;

	@ApiModelProperty(value = "访问开始时间结束日期时间", name = "guest_startTime", notes = "TimeTo")
	private LocalDateTime guestStartTimeTo;

	@ApiModelProperty(value = "访问开始时间", name = "guest_startTime")
	private LocalDateTime guestStartTime;


	@ApiModelProperty(value = "访问结束时间开始日期时间", name = "guest_endTime", notes = "TimeFrom")
	private LocalDateTime guestEndTimeFrom;

	@ApiModelProperty(value = "访问结束时间结束日期时间", name = "guest_endTime", notes = "TimeTo")
	private LocalDateTime guestEndTimeTo;

	@ApiModelProperty(value = "访问结束时间", name = "guest_endTime")
	private LocalDateTime guestEndTime;


	@ApiModelProperty(value = "创建者", name = "create_user")
	private String createUser;


	@ApiModelProperty(value = "创建时间开始日期时间", name = "create_time", notes = "TimeFrom")
	private LocalDateTime createTimeFrom;

	@ApiModelProperty(value = "创建时间结束日期时间", name = "create_time", notes = "TimeTo")
	private LocalDateTime createTimeTo;

	@ApiModelProperty(value = "创建时间", name = "create_time")
	private LocalDateTime createTime;


	@ApiModelProperty(value = "更新人", name = "modified_user")
	private String modifiedUser;


	@ApiModelProperty(value = "更新时间开始日期时间", name = "modified_time", notes = "TimeFrom")
	private LocalDateTime modifiedTimeFrom;

	@ApiModelProperty(value = "更新时间结束日期时间", name = "modified_time", notes = "TimeTo")
	private LocalDateTime modifiedTimeTo;

	@ApiModelProperty(value = "更新时间", name = "modified_time")
	private LocalDateTime modifiedTime;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}


	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}


	public String getGuestIdcard() {
		return guestIdcard;
	}

	public void setGuestIdcard(String guestIdcard) {
		this.guestIdcard = guestIdcard;
	}


	public Integer getGuestIdtype() {
		return guestIdtype;
	}

	public void setGuestIdtype(Integer guestIdtype) {
		this.guestIdtype = guestIdtype;
	}


	public Integer getGuestGender() {
		return guestGender;
	}

	public void setGuestGender(Integer guestGender) {
		this.guestGender = guestGender;
	}


	public String getGuestMobile() {
		return guestMobile;
	}

	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}


	public String getGuestQrcode() {
		return guestQrcode;
	}

	public void setGuestQrcode(String guestQrcode) {
		this.guestQrcode = guestQrcode;
	}


	public LocalDateTime getGuestBirthdayFrom() {
		return guestBirthdayFrom;
	}

	public void setGuestBirthdayFrom (LocalDateTime guestBirthdayFrom) {
		this.guestBirthdayFrom = guestBirthdayFrom;
	}

	public LocalDateTime getGuestBirthdayTo() {
		return guestBirthdayTo;
	}

	public void setGuestBirthdayTo (LocalDateTime guestBirthdayTo) {
		this.guestBirthdayTo = guestBirthdayTo;
	}

	public LocalDateTime getGuestBirthday() {
		return guestBirthday;
	}

	public void setGuestBirthday(LocalDateTime guestBirthday) {
		this.guestBirthday = guestBirthday;
	}


	public String getGuestPhotoUrl() {
		return guestPhotoUrl;
	}

	public void setGuestPhotoUrl(String guestPhotoUrl) {
		this.guestPhotoUrl = guestPhotoUrl;
	}


	public String getGuestSeqname() {
		return guestSeqname;
	}

	public void setGuestSeqname(String guestSeqname) {
		this.guestSeqname = guestSeqname;
	}


	public String getGuestSeqid() {
		return guestSeqid;
	}

	public void setGuestSeqid(String guestSeqid) {
		this.guestSeqid = guestSeqid;
	}


	public String getGuestRemarkt() {
		return guestRemarkt;
	}

	public void setGuestRemarkt(String guestRemarkt) {
		this.guestRemarkt = guestRemarkt;
	}


	public String getIntervieweeName() {
		return intervieweeName;
	}

	public void setIntervieweeName(String intervieweeName) {
		this.intervieweeName = intervieweeName;
	}


	public String getIntervieweeCode() {
		return intervieweeCode;
	}

	public void setIntervieweeCode(String intervieweeCode) {
		this.intervieweeCode = intervieweeCode;
	}


	public String getIntervieweeMobile() {
		return intervieweeMobile;
	}

	public void setIntervieweeMobile(String intervieweeMobile) {
		this.intervieweeMobile = intervieweeMobile;
	}


	public LocalDateTime getGuestStartTimeFrom() {
		return guestStartTimeFrom;
	}

	public void setGuestStartTimeFrom (LocalDateTime guestStartTimeFrom) {
		this.guestStartTimeFrom = guestStartTimeFrom;
	}

	public LocalDateTime getGuestStartTimeTo() {
		return guestStartTimeTo;
	}

	public void setGuestStartTimeTo (LocalDateTime guestStartTimeTo) {
		this.guestStartTimeTo = guestStartTimeTo;
	}

	public LocalDateTime getGuestStartTime() {
		return guestStartTime;
	}

	public void setGuestStartTime(LocalDateTime guestStartTime) {
		this.guestStartTime = guestStartTime;
	}


	public LocalDateTime getGuestEndTimeFrom() {
		return guestEndTimeFrom;
	}

	public void setGuestEndTimeFrom (LocalDateTime guestEndTimeFrom) {
		this.guestEndTimeFrom = guestEndTimeFrom;
	}

	public LocalDateTime getGuestEndTimeTo() {
		return guestEndTimeTo;
	}

	public void setGuestEndTimeTo (LocalDateTime guestEndTimeTo) {
		this.guestEndTimeTo = guestEndTimeTo;
	}

	public LocalDateTime getGuestEndTime() {
		return guestEndTime;
	}

	public void setGuestEndTime(LocalDateTime guestEndTime) {
		this.guestEndTime = guestEndTime;
	}


	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}


	public LocalDateTime getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom (LocalDateTime createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public LocalDateTime getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo (LocalDateTime createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}


	public String getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}


	public LocalDateTime getModifiedTimeFrom() {
		return modifiedTimeFrom;
	}

	public void setModifiedTimeFrom (LocalDateTime modifiedTimeFrom) {
		this.modifiedTimeFrom = modifiedTimeFrom;
	}

	public LocalDateTime getModifiedTimeTo() {
		return modifiedTimeTo;
	}

	public void setModifiedTimeTo (LocalDateTime modifiedTimeTo) {
		this.modifiedTimeTo = modifiedTimeTo;
	}

	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}


}
