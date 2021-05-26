/** 以下代码由代码生成器自动生成，如无必要，请勿修改！2021-5-19 11:09:12 **/
package cn.chinaunicom.person.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;

/**
 * 数据库实体，访客信息
 * @author code-generator
 * @date 2021-5-19 11:09:12
 */
@TableName(value = "guest_info")
public class GuestInfoEntity {

	/**
     * 自增主键
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;

	/**
	 * 来访申请id
	 */
	@TableField("apply_id")
	private Long applyId;


	/**
	 * 来访人姓名
	 */
	@TableField("guest_name")
	private String guestName;


	/**
	 * 来访人身份证号
	 */
	@TableField("guest_idcard")
	private String guestIdcard;


	/**
	 * 来访人证件类型：0身份证
	 */
	@TableField("guest_idtype")
	private Integer guestIdtype;


	/**
	 * 性别: 1表示男性 2表示女性 0表示未设置
	 */
	@TableField("guest_gender")
	private Integer guestGender;


	/**
	 * 来访人手机号
	 */
	@TableField("guest_mobile")
	private String guestMobile;


	/**
	 * 二维码
	 */
	@TableField("guest_qrcode")
	private String guestQrcode;


	/**
	 * 出生日期
	 */
	@TableField("guest_birthday")
	private LocalDateTime guestBirthday;


	/**
	 * 来访人照片
	 */
	@TableField("guest_photo_url")
	private String guestPhotoUrl;


	/**
	 * 来访人权限名称
	 */
	@TableField("guest_seqname")
	private String guestSeqname;


	/**
	 * 来访人权限
	 */
	@TableField("guest_seqid")
	private String guestSeqid;


	/**
	 * 备注
	 */
	@TableField("guest_remarkt")
	private String guestRemarkt;


	/**
	 * 受访人姓名
	 */
	@TableField("interviewee_name")
	private String intervieweeName;


	/**
	 * 受访人编码
	 */
	@TableField("interviewee_code")
	private String intervieweeCode;


	/**
	 * 受访人电话
	 */
	@TableField("interviewee_mobile")
	private String intervieweeMobile;


	/**
	 * 访问开始时间
	 */
	@TableField("guest_startTime")
	private LocalDateTime guestStartTime;


	/**
	 * 访问结束时间
	 */
	@TableField("guest_endTime")
	private LocalDateTime guestEndTime;


	/**
	 * 创建者
	 */
	@TableField("create_user")
	private String createUser;


	/**
	 * 创建时间
	 */
	@TableField("create_time")
	private LocalDateTime createTime;


	/**
	 * 更新人
	 */
	@TableField("modified_user")
	private String modifiedUser;


	/**
	 * 更新时间
	 */
	@TableField("modified_time")
	private LocalDateTime modifiedTime;


	/**
	 * 获取自增主键
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置自增主键
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取来访申请id
	 */
	public Long getApplyId() {
		return applyId;
	}

	/**
	 * 设置来访申请id
	 */
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	/**
	 * 获取来访人姓名
	 */
	public String getGuestName() {
		return guestName;
	}

	/**
	 * 设置来访人姓名
	 */
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	/**
	 * 获取来访人身份证号
	 */
	public String getGuestIdcard() {
		return guestIdcard;
	}

	/**
	 * 设置来访人身份证号
	 */
	public void setGuestIdcard(String guestIdcard) {
		this.guestIdcard = guestIdcard;
	}

	/**
	 * 获取来访人证件类型：0身份证
	 */
	public Integer getGuestIdtype() {
		return guestIdtype;
	}

	/**
	 * 设置来访人证件类型：0身份证
	 */
	public void setGuestIdtype(Integer guestIdtype) {
		this.guestIdtype = guestIdtype;
	}

	/**
	 * 获取性别: 1表示男性 2表示女性 0表示未设置
	 */
	public Integer getGuestGender() {
		return guestGender;
	}

	/**
	 * 设置性别: 1表示男性 2表示女性 0表示未设置
	 */
	public void setGuestGender(Integer guestGender) {
		this.guestGender = guestGender;
	}

	/**
	 * 获取来访人手机号
	 */
	public String getGuestMobile() {
		return guestMobile;
	}

	/**
	 * 设置来访人手机号
	 */
	public void setGuestMobile(String guestMobile) {
		this.guestMobile = guestMobile;
	}

	/**
	 * 获取二维码
	 */
	public String getGuestQrcode() {
		return guestQrcode;
	}

	/**
	 * 设置二维码
	 */
	public void setGuestQrcode(String guestQrcode) {
		this.guestQrcode = guestQrcode;
	}

	/**
	 * 获取出生日期
	 */
	public LocalDateTime getGuestBirthday() {
		return guestBirthday;
	}

	/**
	 * 设置出生日期
	 */
	public void setGuestBirthday(LocalDateTime guestBirthday) {
		this.guestBirthday = guestBirthday;
	}

	/**
	 * 获取来访人照片
	 */
	public String getGuestPhotoUrl() {
		return guestPhotoUrl;
	}

	/**
	 * 设置来访人照片
	 */
	public void setGuestPhotoUrl(String guestPhotoUrl) {
		this.guestPhotoUrl = guestPhotoUrl;
	}

	/**
	 * 获取来访人权限名称
	 */
	public String getGuestSeqname() {
		return guestSeqname;
	}

	/**
	 * 设置来访人权限名称
	 */
	public void setGuestSeqname(String guestSeqname) {
		this.guestSeqname = guestSeqname;
	}

	/**
	 * 获取来访人权限
	 */
	public String getGuestSeqid() {
		return guestSeqid;
	}

	/**
	 * 设置来访人权限
	 */
	public void setGuestSeqid(String guestSeqid) {
		this.guestSeqid = guestSeqid;
	}

	/**
	 * 获取备注
	 */
	public String getGuestRemarkt() {
		return guestRemarkt;
	}

	/**
	 * 设置备注
	 */
	public void setGuestRemarkt(String guestRemarkt) {
		this.guestRemarkt = guestRemarkt;
	}

	/**
	 * 获取受访人姓名
	 */
	public String getIntervieweeName() {
		return intervieweeName;
	}

	/**
	 * 设置受访人姓名
	 */
	public void setIntervieweeName(String intervieweeName) {
		this.intervieweeName = intervieweeName;
	}

	/**
	 * 获取受访人编码
	 */
	public String getIntervieweeCode() {
		return intervieweeCode;
	}

	/**
	 * 设置受访人编码
	 */
	public void setIntervieweeCode(String intervieweeCode) {
		this.intervieweeCode = intervieweeCode;
	}

	/**
	 * 获取受访人电话
	 */
	public String getIntervieweeMobile() {
		return intervieweeMobile;
	}

	/**
	 * 设置受访人电话
	 */
	public void setIntervieweeMobile(String intervieweeMobile) {
		this.intervieweeMobile = intervieweeMobile;
	}

	/**
	 * 获取访问开始时间
	 */
	public LocalDateTime getGuestStartTime() {
		return guestStartTime;
	}

	/**
	 * 设置访问开始时间
	 */
	public void setGuestStartTime(LocalDateTime guestStartTime) {
		this.guestStartTime = guestStartTime;
	}

	/**
	 * 获取访问结束时间
	 */
	public LocalDateTime getGuestEndTime() {
		return guestEndTime;
	}

	/**
	 * 设置访问结束时间
	 */
	public void setGuestEndTime(LocalDateTime guestEndTime) {
		this.guestEndTime = guestEndTime;
	}

	/**
	 * 获取创建者
	 */
	public String getCreateUser() {
		return createUser;
	}

	/**
	 * 设置创建者
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	/**
	 * 获取创建时间
	 */
	public LocalDateTime getCreateTime() {
		return createTime;
	}

	/**
	 * 设置创建时间
	 */
	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取更新人
	 */
	public String getModifiedUser() {
		return modifiedUser;
	}

	/**
	 * 设置更新人
	 */
	public void setModifiedUser(String modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	/**
	 * 获取更新时间
	 */
	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}

	/**
	 * 设置更新时间
	 */
	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}


}
