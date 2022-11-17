package com.example.handlemanager.repository;

import com.example.handlemanager.repositoryobjects.HandleIdx;
import com.example.handlemanager.repositoryobjects.Handles;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface HandleRepository extends JpaRepository<Handles, HandleIdx> {
  // Get all handles
	/*
	@Query(value="select distinct handle from handles h", nativeQuery=true)
	List<byte[]> getHandles();	

	@Query(value="select distinct handle from handles h where data = ?1", nativeQuery=true)
	List<byte[]> getHandles(byte[] pidStatus);*/

  @Query(value = "select distinct handle from handles h where data = ?1 offset ?2 limit ?3", nativeQuery = true)
  List<byte[]> getHandles(byte[] pidStatus, int pageNum, int pageSize);

  @Query(value = "select distinct handle from handles h offset ?1 limit ?2", nativeQuery = true)
  List<byte[]> getHandles(int pageNum, int pageSize);


  // Resolve single handle
  @Query(value = "select * from handles where handle = ?1", nativeQuery = true)
  List<Handles> resolveHandle(byte[] handle);

  // Given list of handles, which ones are already taken?
  @Query(value = "select distinct handle from handles where handle in :hdls", nativeQuery = true)
  List<byte[]> checkDuplicateHandles(List<byte[]> hdls);

  // Given handle and index to modify, update handle record with supplied data
  @Modifying
  @Transactional
  @Query(value = "update handles h set data=:data, timestamp=:ts where h.handle=:hdl and h.idx=:i", nativeQuery = true)
  void updateHandleRecordData(byte[] data, long ts, byte[] hdl, int i);

}


