package com.agb.w2w_iberostar.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SpaceShipAspect {

    private static final Logger logger = LoggerFactory.getLogger(SpaceShipAspect.class);

    @AfterReturning(pointcut = "execution(* com.agb.w2w_iberostar.controller.SpaceShipController.getSpaceshipById(..)) && args(id)", returning = "result")
    public void logNegativeId(JoinPoint joinPoint, Long id, Object result) {
        if (id < 0) {
            logger.warn("Se ha solicitado una nave con un ID negativo: {}", id);
        }
    }

}
