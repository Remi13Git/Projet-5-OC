describe('Auth and Session Management spec', () => {
    it('should register a user, log in, view the user account details, create a session, update the session, view the session details, delete the session', () => {

      // 1. Test d'inscription réussie
      cy.visit('/register')
      cy.intercept('POST', '/api/auth/register', {
        statusCode: 200,
        body: {},
      }).as('registerRequest')
      cy.get('input[formControlName=email]').type('newuser@studio.com')
      cy.get('input[formControlName=firstName]').type('New')
      cy.get('input[formControlName=lastName]').type('User')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type="submit"]').should('not.be.disabled').click()
      cy.wait('@registerRequest').its('response.statusCode').should('eq', 200)
      cy.url().should('include', '/login')

  
      // 2. Test de connexion réussie
      cy.visit('/login')
      cy.intercept('POST', '/api/auth/login', {
        body: {
          id: 1,
          username: 'Teacher',
          firstName: 'New',
          lastName: 'User',
          admin: true,
        },
      }).as('loginRequest')
      const sessions = [
        { id: 1, name: 'Yoga Session', description: 'Relaxation and stretching', time: '10:00 AM' },
        { id: 2, name: 'Pilates Session', description: 'Core strengthening', time: '02:00 PM' },
      ]
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: sessions,
      }).as('sessionsRequest')
      const teachers = [
        {
          id: 1,
          lastName: 'User',
          firstName: 'New',
          createdAt: '2023-01-01T00:00:00.000Z',
          updatedAt: '2023-01-01T00:00:00.000Z',
        },
      ]
      cy.intercept('GET', '/api/teacher', {
        statusCode: 200,
        body: teachers,
      }).as('teachersRequest')
      cy.get('input[formControlName=email]').type('newuser@studio.com')
      cy.get('input[formControlName=password]').type('test!1234')
      cy.get('button[type="submit"]').click()
      cy.wait('@loginRequest')
      cy.url().should('include', '/sessions')


      // 3. Test de récupération des informations de l'utilisateur
      cy.intercept('GET', '/api/user/1', {
        statusCode: 200,
        body: {
          id: 1,
          email: 'newuser@studio.com',
          lastName: 'User',
          firstName: 'New',
          admin: true,
          password: 'test!1234',
          createdAt: '2023-01-01T00:00:00.000Z',
          updatedAt: '2023-01-01T00:00:00.000Z',
        },
      }).as('getUserRequest')
  
      // Cliquer sur "Account" et vérifier les informations de l'utilisateur
      cy.get('span[routerlink="me"]').click()
  
      // Attendre la requête GET et vérifier la réponse
      cy.wait('@getUserRequest').its('response.statusCode').should('eq', 200)
      
      // Retourner sur la page d'accueil
      cy.get('span[routerlink="sessions"]').click()
  

      // 4. Test de création de session
      cy.get('button').contains('Create').click()
      cy.wait('@teachersRequest')
      cy.get('mat-select[formControlName=teacher_id]').click()
      cy.get('mat-option').contains('New User').click()
      const newSession = {
        id: 3,
        name: 'Yoga Session',
        description: 'Relaxation and stretching',
        date: '2025-03-23',
        teacher_id: 1,
      }
      cy.intercept('POST', '/api/session', {
        statusCode: 200,
        body: newSession,
      }).as('createSessionRequest')
      cy.get('input[formControlName=name]').type('Yoga Session')
      cy.get('input[formControlName=date]').type('2025-03-23')
      cy.get('textarea[formControlName=description]').type('Relaxation and stretching')
      cy.get('button[type="submit"]').click()
      cy.wait('@createSessionRequest').its('response.statusCode').should('eq', 200)
      cy.url().should('include', '/sessions')
  
      // Ici, on remocke la liste des sessions pour inclure la nouvelle session
      const updatedSessions = [...sessions, newSession];
      cy.intercept('GET', '/api/session', {
        statusCode: 200,
        body: updatedSessions,
      }).as('updatedSessionsRequest')
      cy.get('.items').should('contain', 'Yoga Session')

  
      // 5. Test de mise à jours de la session
      const sessionToEdit = {
        id: 1,
        name: 'Yoga Session',
        description: 'Relaxation and stretching',
        date: '2025-03-23',
        teacher_id: 1,
      }
      
      // Simuler la récupération de la session avec l'ID 1
      cy.intercept('GET', '/api/session/1', {
        statusCode: 200,
        body: sessionToEdit,
      }).as('getSessionToEdit')
  
      // Cliquer sur le bouton "Edit" dans la carte de la session "Yoga Session"
      cy.contains('mat-card', 'Yoga Session').within(() => {
        cy.get('button').contains('Edit').click()
      });
  
      // Attendre la réponse GET pour récupérer les données de la session à éditer
      cy.wait('@getSessionToEdit')
  
      // Modifier les champs dans le formulaire d'édition
      cy.get('input[formControlName=name]').clear().type('Yoga Session Updated')
      cy.get('textarea[formControlName=description]').clear().type('Updated description')
      
      // Intercepter l'appel PUT pour mettre à jour la session
      const updatedSession = {
        ...sessionToEdit,
        name: 'Yoga Session Updated',
        description: 'Updated description'
      };
      cy.intercept('PUT', '/api/session/1', {
        statusCode: 200,
        body: updatedSession,
      }).as('updateSessionRequest')
      
      // Soumettre le formulaire d'édition
      cy.get('button[type="submit"]').click()
      cy.wait('@updateSessionRequest').its('response.statusCode').should('eq', 200)

  
      // 5. Vérification de la page de DÉTAIL avec l'ID 1
      cy.intercept('GET', '/api/session/1', {
        statusCode: 200,
        body: updatedSession,
      }).as('getUpdatedSessionDetails')
  
      // Accéder à la page de détails de la session avec l'ID 1 (Yoga Session)
      cy.get('mat-card').contains('Detail').click()

  
      // 6. Vérification la suppression d'une session
      cy.intercept('DELETE', '/api/session/1', {
        statusCode: 200,
        body: {},
      }).as('deleteSessionRequest')
  
      // Cliquer sur le bouton "Delete"
      cy.get('button').contains('Delete').click()
  
      // Attendre que la requête DELETE soit effectuée
      cy.wait('@deleteSessionRequest').its('response.statusCode').should('eq', 200)
  
      // Vérifier que la session a été supprimée en vérifiant la redirection
      cy.url().should('not.include', '/sessions/detail/1')
  
      // Vérification que la session supprimée n'est plus présente dans la liste des sessions
      cy.get('.items').should('not.contain', 'Yoga Session Updated')
    });
  });
  