import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BiereComponent } from './biere.component';
import { BiereDetailComponent } from './biere-detail.component';
import { BierePopupComponent } from './biere-dialog.component';
import { BiereDeletePopupComponent } from './biere-delete-dialog.component';

import { Principal } from '../../shared';

export const biereRoute: Routes = [
    {
        path: 'biere',
        component: BiereComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.biere.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'biere/:id',
        component: BiereDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.biere.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bierePopupRoute: Routes = [
    {
        path: 'biere-new',
        component: BierePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.biere.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'biere/:id/edit',
        component: BierePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.biere.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'biere/:id/delete',
        component: BiereDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.biere.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
