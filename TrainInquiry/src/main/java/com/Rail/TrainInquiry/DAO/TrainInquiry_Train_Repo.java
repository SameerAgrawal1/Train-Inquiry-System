package com.Rail.TrainInquiry.DAO;


import org.springframework.data.jpa.repository.JpaRepository;

import com.Rail.TrainInquiry.Entity.TrainEntity;

public interface TrainInquiry_Train_Repo extends JpaRepository<TrainEntity, Integer>
{
	
}
