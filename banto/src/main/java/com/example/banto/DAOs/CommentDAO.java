package com.example.banto.DAOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.banto.Configs.EnvConfig;
import com.example.banto.DTOs.CartDTO;
import com.example.banto.DTOs.CommentDTO;
import com.example.banto.DTOs.ItemDTO;
import com.example.banto.Entitys.Carts;
import com.example.banto.Entitys.Comments;
import com.example.banto.Entitys.DeliverType;
import com.example.banto.Entitys.Items;
import com.example.banto.Entitys.SoldItems;
import com.example.banto.Entitys.Users;
import com.example.banto.Repositorys.CommentRepository;
import com.example.banto.Repositorys.ItemRepository;
import com.example.banto.Repositorys.PayRepository;

import jakarta.transaction.Transactional;

@Component
public class CommentDAO {
	@Autowired
	AuthDAO authDAO;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PayRepository payRepository;
	@Autowired
	ItemRepository itemRepository;
	@Autowired
	EnvConfig envConfig;
	
	@Transactional
	public void writeComment(Integer userId, CommentDTO dto, List<MultipartFile> files) throws Exception{
		try {
			// 인증 유효 확인
			Users user = authDAO.auth(userId);
			Integer soldItemPk = dto.getSoldItemPk();
			Optional<SoldItems> soldItemOpt = payRepository.findById(soldItemPk);
			if(soldItemOpt.isEmpty()) {
				throw new Exception("결제된 적 없는 물품입니다.");
			}
			SoldItems soldItem = soldItemOpt.get();
			if(soldItem.getDeliverInfo() != DeliverType.Delivered) {
				throw new Exception("배송 완료된 물품만 후기를 작성할 수 있습니다.");
			}
			Integer itemPk = soldItem.getItemPk();
			Optional<Items> itemOpt = itemRepository.findById(itemPk);
			if(itemOpt.isEmpty()) {
				throw new Exception("존재하지 않는 물품입니다.");
			}
			if(dto.getContent() == null) {
				throw new Exception("후기 내용을 입력해주세요.");
			}
			if(dto.getStar() == null) {
				throw new Exception("별점을 입력해주세요.");
			}
			if(dto.getStar() < 1 || dto.getStar() > 5) {
				throw new Exception("별점은 1점 이상 5점 이하여야 합니다.");
			}
			// 프로젝트의 프론트엔드 경로
			String savePath = envConfig.get("FRONTEND_UPLOAD_ADDRESS");
			String img = "";
			if(files != null) {				
				for(MultipartFile file : files) {
					String originalfilename = file.getOriginalFilename();
					String before = originalfilename.substring(0, originalfilename.indexOf("."));
					String ext = originalfilename.substring(originalfilename.indexOf("."));
					String newfilename = before + "(" + UUID.randomUUID() + ")" + ext;
					file.transferTo(new java.io.File(savePath + newfilename));
					img += newfilename + "/";
				}
				img = img.substring(0, img.length() - 1);			
			}
			Comments comment = new Comments();
			comment.setContent(dto.getContent());
			comment.setImg(img);
			comment.setItem(itemOpt.get());
			comment.setStar(dto.getStar());
			comment.setUser(user);
			commentRepository.save(comment);
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<CommentDTO> getItemComment(Integer userId, Integer itemId, Integer page) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
			Page<Comments> comments = commentRepository.findCommentsByItemId(itemId, pageable);
			List<CommentDTO> commentList = new ArrayList<CommentDTO>();
			for(Comments comment : comments) {
				CommentDTO dto = CommentDTO.toDTO(comment);
				commentList.add(dto);
			}
			return commentList;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public CommentDTO getComment(Integer userId, Integer commentId) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Optional<Comments> commentOpt = commentRepository.findById(commentId);
			if(commentOpt.isEmpty()) {
				throw new Exception("존재하지 않는 후기입니다.");
			}
			else {
				Comments comment = commentOpt.get();
				CommentDTO dto = CommentDTO.toDTO(comment);
				return dto;
			}
		}catch(Exception e) {
			throw e;
		}
	}
	
	public List<CommentDTO> getMyComment(Integer userId, Integer page) throws Exception{
		try {
			// 인증 유효 확인
			authDAO.auth(userId);
			Pageable pageable = PageRequest.of(page-1, 20, Sort.by("id").ascending());
			Page<Comments> comments = commentRepository.findCommentsByUserId(userId, pageable);
			List<CommentDTO> commentList = new ArrayList<CommentDTO>();
			for(Comments comment : comments) {
				CommentDTO dto = CommentDTO.toDTO(comment);
				commentList.add(dto);
			}
			return commentList;
		}catch(Exception e) {
			throw e;
		}
	}
}
