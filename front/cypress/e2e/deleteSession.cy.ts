describe('Delete Session spec', () => {
    it('should delete the session', () => {

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
      
  
      // 2. Vérification la suppression d'une session
      const sessionToDelete = {
        id: 1,
        name: 'Yoga Session',
        description: 'Relaxation and stretching',
        date: '2025-03-23',
        teacher_id: 1,
      }
      
      // Simuler la récupération de la session avec l'ID 1
      cy.intercept('GET', '/api/session/1', {
        statusCode: 200,
        body: sessionToDelete,
      }).as('getSessionTodelete')
  
  
      // Accéder à la page de détails de la session avec l'ID 1 (Yoga Session)
      cy.get('mat-card').contains('Detail').click()

  
      // Vérification la suppression d'une session
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