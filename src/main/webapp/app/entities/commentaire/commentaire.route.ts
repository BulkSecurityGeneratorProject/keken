import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CommentaireComponent } from './commentaire.component';
import { CommentaireDetailComponent } from './commentaire-detail.component';
import { CommentairePopupComponent } from './commentaire-dialog.component';
import { CommentaireDeletePopupComponent } from './commentaire-delete-dialog.component';

import { Principal } from '../../shared';

export const commentaireRoute: Routes = [
    {
        path: 'commentaire',
        component: CommentaireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'commentaire/:id',
        component: CommentaireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commentairePopupRoute: Routes = [
    {
        path: 'commentaire-new',
        component: CommentairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentaire/:id/edit',
        component: CommentairePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'commentaire/:id/delete',
        component: CommentaireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.commentaire.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
