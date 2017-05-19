import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { UserExtraComponent } from './user-extra.component';
import { UserExtraDetailComponent } from './user-extra-detail.component';
import { UserExtraPopupComponent } from './user-extra-dialog.component';
import { UserExtraDeletePopupComponent } from './user-extra-delete-dialog.component';

import { Principal } from '../../shared';

export const userExtraRoute: Routes = [
    {
        path: 'user-extra',
        component: UserExtraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.userExtra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'user-extra/:id',
        component: UserExtraDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.userExtra.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const userExtraPopupRoute: Routes = [
    {
        path: 'user-extra-new',
        component: UserExtraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.userExtra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-extra/:id/edit',
        component: UserExtraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.userExtra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'user-extra/:id/delete',
        component: UserExtraDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'kekenApp.userExtra.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
