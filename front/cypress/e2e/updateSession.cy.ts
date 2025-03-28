describe('Update Session spec', () => {
    it('should update the session', () => {

      // 1. Test de connexion réussie
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

  
      // 2. Test de mise à jours de la session
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
    });
});