package com.example.banto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.banto.DAOs.CommentDAO;
import com.example.banto.DTOs.CommentDTO;
import com.example.banto.DTOs.ResponseDTO;

@Service
public class CommentService {
	@Autowired
	CommentDAO commentDAO;
	
	public void writeComment(Integer userId, CommentDTO dto, List<MultipartFile> files) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				commentDAO.writeComment(userId, dto, files);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public ResponseDTO getItemComment(Integer userId, Integer itemId, Integer page) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return commentDAO.getItemComment(userId, itemId, page);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public ResponseDTO getComment(Integer userId, Integer commentId) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return commentDAO.getComment(userId, commentId);
			}catch(Exception e) {
				throw e;
			}
		}
	}
	
	public ResponseDTO getMyComment(Integer userId, Integer page) throws Exception{
		if(userId == null) { 
			throw new Exception("권한 없음");
		}
		else {
			try {
				return commentDAO.getMyComment(userId, page);
			}catch(Exception e) {
				throw e;
			}
		}
	}
}
