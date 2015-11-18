/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.caelum.vraptor.musicjungle.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.hibernate.Session;

import br.com.caelum.vraptor.musicjungle.model.Music;
import br.com.caelum.vraptor.musicjungle.model.User;

/**
 * Default implementation for MusicDao.
 *
 * @author Lucas Cavalcanti
 * @author Rodrigo Turini
 */
public class DefaultMusicDao implements MusicDao {

	private final Session session;

	/**
	 * @deprecated CDI eyes only
	 */
	protected DefaultMusicDao() {
		this(null);
	}
	
	/**
	 * Creates a new MusicDao.
	 *
	 * @param entityManager JPA's EntityManager.
	 */
	@Inject
	public DefaultMusicDao(Session session) {
		this.session = session;
	}

	@Override
	public void add(Music music) {
		session.persist(music);
	}

	@Override
	public List<Music> searchSimilarTitle(String title) {
		List<Music> musics = session
			.createQuery("select m from Music m where lower(m.title) like lower(:title)")
				.setParameter("title", "%" + title+ "%")
				.list();
		return musics;
	}
	
	@Override
	public Music load(Music music) {
		return (Music) session
				.createQuery("from Music m where m.id = :id)")
				.setParameter("id", music.getId())
				.uniqueResult();
	}
	
	@Override
	public List<Music> listAll() {
		return session.createQuery("select m from Music m").list();
	}

}
